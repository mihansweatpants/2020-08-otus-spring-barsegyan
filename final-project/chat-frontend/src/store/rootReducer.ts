import { combineReducers } from '@reduxjs/toolkit';

import auth from './auth/authSlice';
import chats from './messenger/chatsSlice';
import messages from './messenger/messagesSlice';
import sessions from './sessions/sessionsSlice';

const rootReducer = combineReducers({
  auth,
  chats,
  messages,
  sessions,
});

export type RootState = ReturnType<typeof rootReducer>;

export default rootReducer;
