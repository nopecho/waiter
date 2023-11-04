'use client';
import React from 'react';
import {useRouter} from "next/navigation";
import Button from "@/app/_components/Button";

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
        <Button
            text={"버틍"}
            onClick={handle}
        />
    );
};

export default DashboardPage;
