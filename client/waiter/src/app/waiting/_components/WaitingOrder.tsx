'use client';
import React, {useEffect, useState} from 'react';
import styled from 'styled-components';
import useAnimateNumber from "@/commons/hooks/useAnimateNumber";
import {useRouter, useSearchParams} from "next/navigation";

interface IWaiting {
    status: string;
    waitingOrder: number;
    destination: string;
}

const WaitingOrder = () => {
    const router = useRouter();
    const [order, setOrder] = useState<number>(0);
    const [orderNumber] = useAnimateNumber(order, 300);

    const params = useSearchParams();
    const managerId = params.get("mid") ?? ""
    const waitingId = params.get("wid") ?? ""

    useEffect(() => {
        const url = `http://localhost:9999/api/v1/stream/events/waiting/${managerId}?waitingId=${waitingId}`;
        const stream: EventSource = new EventSource(url);

        stream.onmessage = (event) => {
            const waiting: IWaiting = JSON.parse(event.data);
            setOrder(waiting.waitingOrder);
        }

        stream.onerror = (event) => {
            console.error("EventSource failed:", event);
        };

        return () => stream.close();
    }, []);


    const handle = () => {
        router.push("/dashboard")
    }

    return (
        <>
            <OrderNumberWrapper>
                대기 번호
                <OrderNumber>
                    {orderNumber.toLocaleString()} 번
                </OrderNumber>

            </OrderNumberWrapper>

            <button onClick={handle}>
                page
            </button>
        </>
    );
};

const OrderNumberWrapper = styled.div`
  font-size: 2em;
  overflow: hidden;
  text-align: center;
  margin-top: 50px;
`;

const OrderNumber = styled.div`
  text-align: center;
`;

export default WaitingOrder;
