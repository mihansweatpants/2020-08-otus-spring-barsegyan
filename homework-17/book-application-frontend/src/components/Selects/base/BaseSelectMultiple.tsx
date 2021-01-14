import React, { useState, useEffect } from 'react';

import {
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  OutlinedInput,
  CircularProgress,
  Typography,
  FormHelperText,
} from '@material-ui/core';
import { SelectProps } from '@material-ui/core/Select';
import { FormControlProps } from '@material-ui/core/FormControl';
import { InputLabelProps } from '@material-ui/core/InputLabel';
import { OutlinedInputProps } from '@material-ui/core/OutlinedInput';

import { useStyles } from './styles';

export interface BaseSelectMultipleProps<T = any> {
  label: string;
  value: T[] | null;
  options?: T[];
  name?: string;
  getOptions?: () => Promise<T[]>;
  onChange: (event: React.ChangeEvent<{ value: T[]; name: string }>) => void;
  getOptionKey: (option: T) => any;
  getOptionLabel: (option: T) => string;
  getOptionDescription?: (option: T) => string;
  SelectProps?: SelectProps;
  FormControlProps?: FormControlProps;
  InputLabelProps?: InputLabelProps;
  InputProps?: Omit<OutlinedInputProps, 'labelWidth'>;
  error?: boolean;
  helperText?: string;
}

function BaseSelectMultiple<T = any>({
  label,
  value = [],
  options,
  getOptions,
  getOptionLabel,
  getOptionKey,
  onChange,
  name,
  SelectProps,
  FormControlProps,
  InputLabelProps,
  InputProps,
  getOptionDescription,
  error,
  helperText,
}: BaseSelectMultipleProps<T>) {
  const styles = useStyles();

  const [items, setItems] = useState<T[]>([...(options || [])]);
  const [isPending, setIsPending] = useState(false);

  useEffect(
    () => {
      if (options) {
        return;
      }

      setIsPending(true);

      if (getOptions) {
        getOptions().then((options) => {
          setItems(options);
          setIsPending(false);
        });
      }
    },
    [getOptions, options],
  );

  const getOptionByKey = (key: string) => [...items, ...(value || [])].find(option => key === getOptionKey(option))!;

  return (
    <FormControl variant="outlined" fullWidth {...FormControlProps} error={error}>
      <InputLabel {...InputLabelProps}>
        {label}
      </InputLabel>

      <Select
        input={<OutlinedInput {...InputProps} disabled={(SelectProps || {}).disabled} labelWidth={9 * label.length} />}
        renderValue={value => Array.isArray(value) && value.map(getOptionByKey).map(getOptionLabel).join(', ')}
        MenuProps={{
          getContentAnchorEl: null,
          anchorOrigin: { vertical: 'bottom', horizontal: 'center' },
          transformOrigin: { vertical: 'top', horizontal: 'center' },
        }}
        value={value?.map(getOptionKey)}
        onChange={(event) => {
          event.target.value = (event.target.value as string[]).map(getOptionByKey);
          onChange(event as React.ChangeEvent<{ value: T[]; name: string }>);
        }}
        name={name}
        multiple
        {...SelectProps}
      >
        {
          isPending && (
            <MenuItem className={styles.spinner}>
              <CircularProgress />
            </MenuItem>
          )
        }

        {
          !isPending && items.map(option => (
            <MenuItem
              value={getOptionKey(option)}
              key={getOptionKey(option)}
              className={styles.menuItem}
            >
              <Typography>
                {getOptionLabel(option)}
              </Typography>

              {
                getOptionDescription != null && (
                  <Typography className={styles.optionDescription}>
                    {getOptionDescription(option)}
                  </Typography>
                )
              }
            </MenuItem>
          ))
        }
      </Select>

      {
        helperText != null && (
          <FormHelperText>{helperText}</FormHelperText>
        )
      }
    </FormControl>
  );
}

export default BaseSelectMultiple;
