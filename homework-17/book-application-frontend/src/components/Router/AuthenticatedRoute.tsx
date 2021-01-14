import React, { FC } from 'react';
import { RouteProps, Route, Redirect } from 'react-router-dom';

import { useAppContext } from 'context/AppContext';

const AuthenticatedRoute: FC<RouteProps> = ({
  render,
  children,
  component,
  ...rest
}) => {
  const { user } = useAppContext();

  return (
    <Route
      {...rest}
      render={(...props) => {
        if (user == null) {
          return <Redirect to="/login" />;
        }

        return (
          <Route {...props} children={children} component={component} render={render} />
        );
      }}
    />
  );
};

export default AuthenticatedRoute;
