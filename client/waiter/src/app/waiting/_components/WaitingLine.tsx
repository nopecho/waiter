'use client';
import React from 'react';
import {useRouter} from "next/navigation";
import useAnimateNumber from "@/commons/hooks/useAnimateNumber";
import {DurationEffect} from "@/commons/enums/enums";
import useWaitingEvent from "@/app/waiting/_components/_hooks/useWaitingEvent";
import Text from "@/app/_components/Text";
import Head from "@/app/_components/Head";
import Button from "@/app/_components/Button";

const WaitingLine = () => {
    const waiting = useWaitingEvent();
    const orderNumber = useAnimateNumber(waiting.waitingOrder, DurationEffect._300);

    const router = useRouter();
    const handle = () => {
        router.push("/dashboard")
    }

    return (
        <>
            <Head text={"대기 번호"}/>
            <Text value={waiting.status} className={"font-bold text-red-800"}/>
            <Text value={orderNumber.toLocaleString()}/>
            <br/>
            <Button
                text={"입장"}
                onClick={handle}
                disabled={waiting.status == "WAIT"}
            />
        </>
    );
};

export default WaitingLine;
