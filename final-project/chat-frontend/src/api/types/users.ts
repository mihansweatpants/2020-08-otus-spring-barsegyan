import { PaginationRequestParams } from "./base/request";

export interface UserDto {
  id: number;
  username: string;
  email: string;
  isOnline: boolean;
}

export interface SearchUsersRequestParams extends PaginationRequestParams {
  searchText: string;
}
