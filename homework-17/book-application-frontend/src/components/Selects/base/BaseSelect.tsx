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

export interface BaseSelectProps<T = any> {
  label: string;
  value: T | null;
  options?: T[];
  name?: string;
  getOptions?: () => Promise<T[]>;
  onChange: (event: React.ChangeEvent<{ value: T; name: string }>, child: React.ReactNode) => void;
  getOptionKey: (option: T) => any;
  getOptionLabel: (option: T) => string;
  getOptionDescription?: (option: T) => string;
  getIsOptionDisabled?: (option: T) => boolean;
  SelectProps?: SelectProps;
  FormControlProps?: FormControlProps;
  InputLabelProps?: InputLabelProps;
  InputProps?: Omit<OutlinedInputProps, 'labelWidth'>;
  error?: boolean;
  helperText?: string;
}

function BaseSelect<T = any>({
  label,
  value,
  options,
  getOptions,
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
  getIsOptionDisabled = () => false,
  ...props
}: BaseSelectProps<T>) {
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

  const getOptionLabel: typeof props.getOptionLabel = (option) => {
    try {
      return props.getOptionLabel(option);
    }
    catch {
      return '';
    }
  };

  return (
    <FormControl variant="outlined" fullWidth {...FormControlProps} error={error}>
      <InputLabel {...InputLabelProps}>
        {label}
      </InputLabel>

      <Select
        input={<OutlinedInput {...InputProps} disabled={(SelectProps || {}).disabled} labelWidth={9 * label.length} />}
        renderValue={() => getOptionLabel(value as T)}
        MenuProps={{
          getContentAnchorEl: null,
          anchorOrigin: { vertical: 'bottom', horizontal: 'center' },
          transformOrigin: { vertical: 'top', horizontal: 'center' },
        }}
        value={value}
        onChange={onChange as any}
        name={name}
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
              value={option as any}
              key={getOptionKey(option)}
              className={styles.menuItem}
              disabled={getIsOptionDisabled(option)}
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

        {
          !isPending && items.length === 0 && (
            <Typography align="center" className={styles.emptyMessage}>
              Данных не найдено
            </Typography>
          )
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

export default BaseSelect;
