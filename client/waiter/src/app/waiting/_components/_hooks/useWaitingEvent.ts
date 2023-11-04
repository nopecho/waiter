import {useEffect, useState} from 'react';
import {useSearchParams} from "next/navigation";

export interface IWaiting {
    status: "WAIT" | "PASS";
    waitingOrder: number;
    destination: string;
}

const INIT: IWaiting = {
    status: "WAIT",
    waitingOrder: 0,
    destination: ""
}

const useWaitingEvent = () => {
    const params = useSearchParams();
    const managerId = params.get("mid") ?? ""
    const waitingId = params.get("wid") ?? ""

    const [waiting, setWaiting] = useState<IWaiting>(INIT);

    useEffect(() => {
        if (!managerId || !waitingId) return;

        const endpoint = "http://localhost:9999";
        const path = `/api/v1/stream/events/waiting/${managerId}`;
        const url = `${endpoint}${path}?waitingId=${waitingId}`;

        const stream = new EventSource(url);

        stream.onmessage = (event) => {
            const data: IWaiting = JSON.parse(event.data);
            setWaiting(data);
        };

        stream.onerror = (event) => {
            console.error("EventSource failed:", event);
        };

        return () => stream.close();
    }, [managerId, waitingId]);

    const isWait = waiting.status === "WAIT";

    return [waiting, isWait];
};

export default useWaitingEvent;