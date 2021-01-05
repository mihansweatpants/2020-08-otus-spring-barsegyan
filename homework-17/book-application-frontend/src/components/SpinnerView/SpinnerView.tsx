import React, { ReactNode, useState, useEffect } from 'react';
import Loader from './Loader';

interface Props<R> {
  children: (response: R) => ReactNode;

  className?: string;
  containerClassName?: string;

  resolve: () => Promise<any>;
}

export default function SpinnerView<R = any>(props: Props<R>) {
  const [isVisible, setIsVisible] = useState(true);
  const [response, setResponse] = useState<R>();
  const { resolve, children, ...rest } = props;

  useEffect(
    () => {
      resolve().then((response) => {
        setResponse(response);
        setIsVisible(false);
      });
    },
    [resolve],
  );

  return (
    <>
      {isVisible ? <Loader {...rest} /> : (
        children(response!)
      )}
    </>
  );
}
