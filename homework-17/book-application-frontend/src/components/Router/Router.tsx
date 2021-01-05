import React, { FC } from 'react';

import { BrowserRouter, Switch, Route } from 'react-router-dom';
import AuthenticatedRoute from './AuthenticatedRoute';
import GuestRoute from './GuestRoute';

import { MainView, NotFound, BookView, Layout, CreateBookView, LoginView } from 'components';

const withLayout = <P extends object>(Component: React.ComponentType<P>): React.FC<P> => (props) => (
  <Layout>
    <Component {...props} />
  </Layout>
);

const Router: FC = () => {
  return (
    <BrowserRouter>
      <Switch>
        <AuthenticatedRoute path="/" exact component={withLayout(MainView)} />
        <AuthenticatedRoute path="/books/create" exact component={withLayout(CreateBookView)} />
        <AuthenticatedRoute path="/books/:bookId" exact component={withLayout(BookView)} />
        <GuestRoute path="/login" exact component={LoginView} />
        <Route component={NotFound} />
      </Switch>
    </BrowserRouter>
  );
};

export default Router;
