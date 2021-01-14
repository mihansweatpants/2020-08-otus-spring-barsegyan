import React, { FC } from 'react';

import { Tabs, Tab } from '@material-ui/core';

import { MainViewTab } from 'components/types';

interface Props {
  activeTab: MainViewTab;
  onChange: (value: MainViewTab) => void;
}

const MainViewTabs: FC<Props> = ({
  activeTab,
  onChange
}) => {

  return (
    <Tabs
      value={activeTab}
      onChange={(_, tab) => onChange(tab)}
      indicatorColor="primary"
    >
      <Tab value={MainViewTab.Books} label="Книги" />
      <Tab value={MainViewTab.Authors} label="Авторы" />
      <Tab value={MainViewTab.Genres} label="Жанры" />
    </Tabs>
  );
};

export default MainViewTabs;
