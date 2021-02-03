import { createSlice, PayloadAction } from '@reduxjs/toolkit';

import { ChatsApi } from 'api';
import { ChatDto } from 'api/types/chats';

import { AppThunk } from 'store';

interface State {
  chatsList: ChatDto[];
}

const initialState: State = {
  chatsList: [],
};

const auth = createSlice({
  name: 'chats',
  initialState,
  reducers: {
    setChatsList(state, { payload }: PayloadAction<ChatDto[]>) {
      state.chatsList = payload;
    },
  },
});

export const {
  setChatsList,
} = auth.actions;

export default auth.reducer;

export const fetchChats = (): AppThunk => async (dispatch) => {
  const chats = await ChatsApi.getAllUserChats();
  dispatch(setChatsList(chats));
};
