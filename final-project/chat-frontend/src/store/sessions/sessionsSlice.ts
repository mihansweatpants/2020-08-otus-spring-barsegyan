import { createSlice, PayloadAction } from '@reduxjs/toolkit';

import { SessionsApi } from 'api';
import { SessionDto } from 'api/types/sessions';

import { AppThunk } from 'store';

interface State {
  sessions: SessionDto[];
  isLoading: boolean;
}

const initialState: State = {
  sessions: [],
  isLoading: false,
};

const sessions = createSlice({
  name: 'sessions',
  initialState,
  reducers: {
    setSessionsListLoading(state) {
      state.isLoading = true;
    },

    setSessionsListLoaded(state) {
      state.isLoading = false;
    },

    setSessionsList(state, { payload }: PayloadAction<SessionDto[]>) {
      state.sessions = payload;
    },
  },
});

export const {
  setSessionsList,
  setSessionsListLoaded,
  setSessionsListLoading
} = sessions.actions;

export default sessions.reducer;

export const fetchUserSessions = (): AppThunk => async (dispatch) => {
  dispatch(setSessionsListLoading());

  const sessions = await SessionsApi.getUserSessions();
  dispatch(setSessionsList(sessions));

  dispatch(setSessionsListLoaded());
};
