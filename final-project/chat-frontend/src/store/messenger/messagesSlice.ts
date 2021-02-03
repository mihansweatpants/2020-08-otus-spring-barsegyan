import { createSlice, PayloadAction } from '@reduxjs/toolkit';

import { ChatsApi } from 'api';
import { ChatMessageDto } from 'api/types/chats';

import { AppThunk } from 'store';
import { PaginationResponse } from 'api/types/base/response';

interface State {
  messagesList: {
    items: ChatMessageDto[],
    totalItems: number,
    totalPages: number,
    page: number,
    limit: number,
  };
}

const initialState: State = {
  messagesList: {
    items: [],
    totalItems: 0,
    totalPages: 0,
    page: 0,
    limit: 100,
  },
};

const auth = createSlice({
  name: 'chats',
  initialState,
  reducers: {
    setMessagesList(state, { payload }: PayloadAction<PaginationResponse<ChatMessageDto>>) {
      state.messagesList.items = payload.items;
      state.messagesList.totalItems = payload.totalItems;
      state.messagesList.totalPages = payload.totalPages;
    },

    pushNewMessage(state, { payload }: PayloadAction<ChatMessageDto>) {
      state.messagesList.items.push(payload);

      const updatedTotalItems = state.messagesList.totalItems + 1;
      state.messagesList.totalItems = updatedTotalItems;

      state.messagesList.totalPages = Math.floor(updatedTotalItems / state.messagesList.limit);
    },

    resetMessagesListState() {
      return initialState;
    }
  },
});

export const {
  setMessagesList,
  resetMessagesListState,
} = auth.actions;

export default auth.reducer;

export const fetchMessages = (chatId: string): AppThunk => async (dispatch, getState) => {
  const { page, limit } = getState().messages.messagesList;

  const messages = await ChatsApi.getChatMessages(chatId, { page, limit });

  dispatch(setMessagesList(messages));
};
