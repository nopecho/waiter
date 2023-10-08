'use client';
import React, {useEffect, useRef, useState} from 'react';
import styled, {css, keyframes} from 'styled-components';

const fadeOut = keyframes`
  from {
    opacity: 1;
  }
  to {
    opacity: 0;
  }
`;

const slideUp = keyframes`
  from {
    transform: translateY(100%);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
`;

const slideDown = keyframes`
  from {
    transform: translateY(-100%);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
`;

const NumberContainer = styled.div`
  height: 40px;
  width: 100px;
  font-size: 2em;
  overflow: hidden;
`;

const Number = styled.div`
  text-align: center;
  position: absolute;
  animation: ${(props) =>
          props.isCurrent // Check if the number is the current number
                  ? (props.direction === 'up'
                          ? css`${slideUp} 0.2s ease-out`
                          : css`${slideDown} 0.2s ease-out`)
                  : css`${fadeOut} 0.1s ease-out forwards`}; // if it's the old number, fade it out
`;

const App = () => {
    const [number, setNumber] = useState(0);
    const [direction, setDirection] = useState('up'); // Add direction state
    const prevNumberRef = useRef();

    useEffect(() => {
        prevNumberRef.current = number;
    }, [number]);

    const increase = () => {
        setDirection('up'); // Set direction up on increase
        setNumber((prev) => prev + 1);
    };

    const decrease = () => {
        setDirection('down'); // Set direction down on decrease
        setNumber((prev) => prev - 1);
    };

    return (
        <div style={{textAlign: 'center', marginTop: '50px'}}>
            <button onClick={increase}>Increase</button>
            <button onClick={decrease}>Decrease</button>
            <NumberContainer>
                {(prevNumberRef.current !== number) && (
                    <Number key={prevNumberRef.current} direction={direction} isCurrent={false}>
                        {prevNumberRef.current}
                    </Number>
                )}
                <Number key={number} direction={direction} isCurrent={true}>
                    {number}
                </Number>
            </NumberContainer>
        </div>
    );
};

export default App;
