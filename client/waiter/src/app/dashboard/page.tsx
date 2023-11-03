'use client';
import React from 'react';
import {useRouter} from "next/navigation";

interface IWaitingResponse {
    redirectUri: string;
}

const DashboardPage = () => {

    const router = useRouter();

    const handle = async () => {
        let response = await fetch("http://localhost:9999/api/v1/waiting", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                destination: {
                    url: "http://localhost:9999/1"
                }
            }),
        });
        const data: IWaitingResponse = await response.json();
        router.replace(data.redirectUri);
    }

    return (
        <button onClick={() => handle()}>
            버튼!!
        </button>
    );
};

export default DashboardPage;
