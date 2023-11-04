export interface IProps {
    text: string;
    onClick?: () => void;
    className?: string;
    disabled?: boolean;
}

const Button = ({
                    text,
                    onClick,
                    className = "w-36 max-w-md h-8 disabled:bg-gray-800 hover:bg-teal-500 rounded-lg border-transparent bg-teal-800 text-base text-center",
                    disabled = false
                }: IProps) => {

    return (
        <button
            className={className}
            onClick={onClick}
            disabled={disabled}
        >
            {text}
        </button>
    )
}

export default Button;