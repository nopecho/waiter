export interface IProps {
    text: string;
    className?: string;
}

const Head = ({text, className = ""}: IProps) => {
    return (
        <h1 className={className}>
            {text}
        </h1>
    )
}

export default Head;