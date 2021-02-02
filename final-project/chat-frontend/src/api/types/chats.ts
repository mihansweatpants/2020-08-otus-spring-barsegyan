import { UserDto } from './user';

export interface ChatDto {
  id: string;
  name: string;
  lastMessage: ChatMessageDto;
  members: UserDto[];
}

export interface ChatMessageDto {
  id: string;
  sentAt: string;
  sentBy: UserDto;
  text: string;
}

export interface CreateChatDto {
  chatName: string;
  memberIds: string[];
}

export interface UpdateChatDto {
  chatName: string;
}
