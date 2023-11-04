export interface IProps {
    value: string;
    className?: string;
}

const Text = ({value, className = ""}: IProps) => {
    return (
        <p className={className}>
            {value}
        </p>
    )
}

export default Text;