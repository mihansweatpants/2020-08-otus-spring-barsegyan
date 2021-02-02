import { combineReducers } from '@reduxjs/toolkit';

import auth from './auth/authSlice';

const rootReducer = combineReducers({
  auth,
});

export type RootState = ReturnType<typeof rootReducer>;

export default rootReducer;
