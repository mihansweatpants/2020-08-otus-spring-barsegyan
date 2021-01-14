import React, { FC, useContext, useState, useCallback } from 'react';

import { SpinnerView } from 'components';

import { AuthApi } from 'api';
import { UserDto } from 'api/types/user/user';

interface ContextProps {
  user: UserDto | null;
  setUser: (user: UserDto | null) => void;
}

const AppContext = React.createContext<ContextProps>({
  user: null,
  setUser: () => {},
});

export const AppContextProvider: FC = ({ children }) => {

  const [user, setUser] = useState<UserDto | null>(null);

  const initializeContext = useCallback(
    async () => {
      try {
        const currentUser = await AuthApi.getCurrentUser();
        setUser(currentUser);
      }
      catch {}
    },
    [],
  );

  return (
    <SpinnerView resolve={initializeContext}>
      {() => (
        <AppContext.Provider
          value={{ user, setUser }}
        >
          {children}
        </AppContext.Provider>
      )}
    </SpinnerView>
  );
};

export const useAppContext = () => useContext(AppContext);
