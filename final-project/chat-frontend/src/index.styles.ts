import { makeStyles } from '@material-ui/core/styles';

export const useGlobalStyles = makeStyles(theme => ({
  '@global': {
    'body': {
      fontFamily: '"Roboto", sans-serif',
    },

    '*::-webkit-scrollbar': {
      width: '0.4em',
    },
    '*::-webkit-scrollbar-track': {
    },
    '*::-webkit-scrollbar-thumb': {
      borderRadius: '8px',
    }
  }
}));
