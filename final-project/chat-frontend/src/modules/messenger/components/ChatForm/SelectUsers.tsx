import React, { FC, useState, useEffect, useCallback } from 'react';
import { negate } from 'lodash';

import {
  TextField,
  Chip,
  Avatar,
  List,
  ListItem,
  ListItemText,
  ListItemAvatar,
  ListItemSecondaryAction,
  Badge,
} from '@material-ui/core';
import { CheckCircleRounded as CheckCircleRoundedIcon } from '@material-ui/icons';

import { UsersApi } from 'api';
import { UserDto } from 'api/types/users';

import { useSelector } from 'store';

import { usePagination } from 'utils/usePagination';
import { useDebounce } from 'utils/useDebounce';
import { stringToHexColor } from 'utils/colors';
import { highlightText } from 'utils/hightlightText';

import { useSelectUsersStyles } from './styles';

interface Props {
  value: UserDto[];
  onChange: (newValue: UserDto[]) => void;
}

const SelectUsers: FC<Props> = () => {
  const styles = useSelectUsersStyles();

  const { limit, page, setTotalItems } = usePagination(100);
  const [foundUsers, setFoundUsers] = useState<UserDto[]>([]);

  const [searchText, setSearchText] = useState('');
  const debouncedSearchText = useDebounce(searchText, 300);

  const handleChangeSearchText = ({ target: { value } }: React.ChangeEvent<HTMLInputElement>) => setSearchText(value);

  const currentUser = useSelector(state => state.auth.user);
  const isCurrentUser = useCallback((user: UserDto) => currentUser?.id === user.id, [currentUser]);

  useEffect(
    () => {
      (async () => {
        if (debouncedSearchText) {
          const { items, totalItems } = await UsersApi.searchUsers({ page, limit, searchText: debouncedSearchText });
          setTotalItems(totalItems);
          setFoundUsers(items.filter(negate(isCurrentUser)));
        }
        else {
          setTotalItems(0);
          setFoundUsers([]);
        }
      })();
    },
    [page, limit, debouncedSearchText, setTotalItems, isCurrentUser]
  );

  const [selectedUsers, setSelectedUsers] = useState<UserDto[]>([]);
  const isUserSelected = (user: UserDto) => selectedUsers.find(selectedUser => selectedUser.id === user.id) != null;

  const unselectUser = (user: UserDto) => setSelectedUsers(selectedUsers => selectedUsers.filter(selectedUser => selectedUser.id !== user.id));
  const selectUser = (user: UserDto) => setSelectedUsers(selectedUsers => [...selectedUsers, user]);

  const toggleUser = (user: UserDto) => isUserSelected(user) ? unselectUser(user) : selectUser(user);

  return (
    <div>
      <div>
        <TextField
          value={searchText}
          onChange={handleChangeSearchText}
          placeholder="Who would you like to add?"
          variant="outlined"
          fullWidth
          InputProps={{
            classes: {
              root: styles.searchInputRoot,
              input: styles.searchInput,
            },
            startAdornment: (
              selectedUsers.map(user => (
                <Chip
                  key={user.id}
                  label={user.username}
                  color="primary"
                  className={styles.chip}
                  onDelete={() => unselectUser(user)}
                  avatar={
                    <Avatar style={{ backgroundColor: stringToHexColor(user.username) }}>
                      {user.username[0].toUpperCase()}
                    </Avatar>
                  }
                />
              ))
            )
          }}
        />
      </div>

      <List className={styles.list}>
        {
          foundUsers.map(foundUser => (
            <ListItem button onClick={() => toggleUser(foundUser)}>
              <ListItemAvatar>
                <Badge
                  variant="dot"
                  invisible={!foundUser.isOnline}
                  anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'right',
                  }}
                >
                  <Avatar style={{ backgroundColor: stringToHexColor(foundUser.username) }}>
                    {foundUser.username[0].toUpperCase()}
                  </Avatar>
                </Badge>
              </ListItemAvatar>
              <ListItemText primary={highlightText(foundUser.username)} secondary={highlightText(foundUser.email)} />
              {
                isUserSelected(foundUser) && (
                  <ListItemSecondaryAction>
                    <CheckCircleRoundedIcon color="primary" />
                  </ListItemSecondaryAction>
                )
              }
            </ListItem>
          ))
        }
      </List>
    </div>
  );
};

export default SelectUsers;
