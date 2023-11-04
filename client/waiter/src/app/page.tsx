import Button from "@/app/_components/Button";
import Link from "next/link";

const HomePage = () => {

    return (
        <main className="flex min-h-screen flex-col items-center justify-between p-24">
            <Link href={"/dashboard"} className={"flex min-w-screen"}>
                <Button text={"click"}/>
            </Link>
        </main>
    )
}

export default HomePage