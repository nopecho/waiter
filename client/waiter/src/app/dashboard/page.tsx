import React from 'react';
import Image from "next/image";

const DashboardPage = () => {
    return (
        <div className="z-10 max-w-5xl w-full items-center justify-between font-mono text-sm lg:flex">
            <Image
                fill
                className="relative dark:drop-shadow-[0_0_0.3rem_#ffffff70] dark:invert"
                src="https://images.unsplash.com/photo-1696706679350-cc9bd724aa9c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2940&q=100"
                alt="image"
                priority
            />
        </div>
    );
};

export default DashboardPage;
