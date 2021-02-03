import { combineReducers } from '@reduxjs/toolkit';

import auth from './auth/authSlice';
import chats from './messenger/chatsSlice';

const rootReducer = combineReducers({
  auth,
  chats,
});

export type RootState = ReturnType<typeof rootReducer>;

export default rootReducer;
