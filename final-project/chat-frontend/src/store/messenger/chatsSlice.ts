import { createSlice, PayloadAction } from '@reduxjs/toolkit';

import { ChatsApi } from 'api';
import { ChatDto, ChatMessageDto } from 'api/types/chats';

import { AppThunk } from 'store';

interface State {
  chatsList: ChatDto[];
  isLoading: boolean;
  selectedChat: ChatDto | null;
}

const initialState: State = {
  chatsList: [],
  isLoading: false,
  selectedChat: null,
};

const auth = createSlice({
  name: 'chats',
  initialState,
  reducers: {
    setChatsListLoading(state) {
      state.isLoading = true;
    },

    setChatsListLoaded(state) {
      state.isLoading = false;
    },

    setChatsList(state, { payload }: PayloadAction<ChatDto[]>) {
      state.chatsList = payload;
    },

    setSelectedChat(state, { payload }: PayloadAction<ChatDto>) {
      state.selectedChat = payload;
    },

    updateChatsListWithNewMessage(state, { payload: newMessage }: PayloadAction<ChatMessageDto>) {
      const updatedAndSortedChatsList = state.chatsList
          .map(chat => newMessage.chatId === chat.id ? ({ ...chat, lastMessage: newMessage }) : chat)
          .sort((prev, next) => {
            if (prev.lastMessage == null || next.lastMessage == null) return -1;

            return +new Date(next.lastMessage.sentAt) - +new Date(prev.lastMessage.sentAt);
          });

      state.chatsList = updatedAndSortedChatsList;
    },
  },
});

export const {
  setChatsList,
  setSelectedChat,
  setChatsListLoading,
  setChatsListLoaded,
  updateChatsListWithNewMessage,
} = auth.actions;

export default auth.reducer;

export const fetchChats = (): AppThunk => async (dispatch) => {
  dispatch(setChatsListLoading());

  const chats = await ChatsApi.getAllUserChats();
  dispatch(setChatsList(chats));

  dispatch(setChatsListLoaded());
};
