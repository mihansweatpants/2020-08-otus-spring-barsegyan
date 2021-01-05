import React, { FC, useState } from 'react';
import { useHistory } from 'react-router-dom';

import { Paper, Button } from '@material-ui/core';

import { MainViewTabs, BooksList, AuthorsList, GenresList } from 'components';
import { MainViewTab } from 'components/types';

import { useStyles } from './styles';

const MainView: FC = () => {
  const styles = useStyles();

  const history = useHistory();

  const [activeTab, setActiveTab] = useState(MainViewTab.Books);

  return (
    <>
      <Paper className={styles.paper}>
        <div className={styles.toolbar}>
          <MainViewTabs
            activeTab={activeTab}
            onChange={setActiveTab}
          />

          <Button onClick={() => history.push('/books/create')} color="primary">
            Добавить книгу
          </Button>
        </div>
      </Paper>

      <Paper className={styles.paper}>
        {activeTab === MainViewTab.Books && <BooksList />}
        {activeTab === MainViewTab.Authors && <AuthorsList />}
        {activeTab === MainViewTab.Genres && <GenresList />}
      </Paper>
    </>
  );
};

export default MainView;
