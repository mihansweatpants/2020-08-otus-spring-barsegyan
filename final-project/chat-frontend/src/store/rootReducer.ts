import { combineReducers } from '@reduxjs/toolkit';

import auth from './auth/authSlice';
import chats from './messenger/chatsSlice';
import messages from './messenger/messagesSlice';
import settings from './settings/settingsSlice';
import sessions from './settings/sessionsSlice';

const rootReducer = combineReducers({
  auth,
  chats,
  messages,
  sessions,
  settings,
});

export type RootState = ReturnType<typeof rootReducer>;

export default rootReducer;
