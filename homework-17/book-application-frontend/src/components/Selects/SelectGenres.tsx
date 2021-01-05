import React, { FC } from 'react';

import { GenresApi } from 'api';
import { GenreDto } from 'api/types/books/genres';

import BaseSelectMultiple, { BaseSelectMultipleProps } from './base/BaseSelectMultiple';

type Props = Pick<
  BaseSelectMultipleProps<GenreDto>,
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

const SelectGenres: FC<Props> = (props) => {
  return (
    <BaseSelectMultiple<GenreDto>
      label="Жанры"
      getOptions={GenresApi.getFullList}
      getOptionKey={({ id }) => id}
      getOptionLabel={({ name }) => name}
      {...props}
    />
  );
};

export default SelectGenres;
