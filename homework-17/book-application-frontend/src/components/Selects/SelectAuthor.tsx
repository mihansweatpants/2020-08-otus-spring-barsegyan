import React, { FC } from 'react';

import { AuthorsApi } from 'api';
import { AuthorDto } from 'api/types/books/authors';

import BaseSelect, { BaseSelectProps } from './base/BaseSelect';

type Props = Pick<
  BaseSelectProps<AuthorDto>,
  | 'onChange'
  | 'value'
  | 'name'
  | 'FormControlProps'
  | 'InputLabelProps'
  | 'InputProps'
  | 'SelectProps'
  | 'error'
  | 'helperText'
>;

const SelectAuthor: FC<Props> = (props) => {
  return (
    <BaseSelect<AuthorDto>
      label="Автор"
      getOptions={AuthorsApi.getFullList}
      getOptionKey={({ id }) => id}
      getOptionLabel={({ name }) => name}
      {...props}
    />
  );
};

export default SelectAuthor;
