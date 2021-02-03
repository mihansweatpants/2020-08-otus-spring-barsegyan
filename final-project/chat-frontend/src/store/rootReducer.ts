import { combineReducers } from '@reduxjs/toolkit';

import auth from './auth/authSlice';
import chats from './messenger/chatsSlice';
import messages from './messenger/messagesSlice';

const rootReducer = combineReducers({
  auth,
  chats,
  messages,
});

export type RootState = ReturnType<typeof rootReducer>;

export default rootReducer;
