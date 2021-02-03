import { format, differenceInCalendarDays } from 'date-fns';

const UTC_OFFSET = new Date().getTimezoneOffset() / 60;

export const toLocalTime = (value: Date | string) => {
  const date = typeof value === 'string' ? new Date(value) : value;

  return new Date(date.setHours(date.getHours() - UTC_OFFSET));
};

export const formatToShortTime = (date: string | Date): string => {
  const dateToFormat = toLocalTime(date);
  const now = new Date();

  if (differenceInCalendarDays(now, dateToFormat) > 1) {
    return format(dateToFormat, 'iii');
  }

  return format(dateToFormat, 'HH:mm');
}
