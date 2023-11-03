import {useEffect, useRef, useState} from 'react';

const useAnimateNumber = (end, duration) => {
    const start = Math.floor(end * 0.7);
    const [number, setNumber] = useState<number>(start);
    const ref = useRef();

    useEffect(() => {
        const startTime = performance.now();
        const animate = (currentTime) => {
            const timeElapsed = currentTime - startTime;
            const progress = Math.min(timeElapsed / duration, 1);
            const newNumber = Math.floor(start + (end - start) * progress);

            setNumber(newNumber);

            if (progress < 1) {
                ref.current = requestAnimationFrame(animate);
            }
        };

        ref.current = requestAnimationFrame(animate);

        return () => {
            if (ref.current) {
                cancelAnimationFrame(ref.current);
            }
        };
    }, [start, end, duration]);

    return [number];
};

export default useAnimateNumber;