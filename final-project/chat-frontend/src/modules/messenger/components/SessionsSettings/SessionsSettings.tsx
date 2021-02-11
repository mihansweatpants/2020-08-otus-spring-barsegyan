import React, { FC, useEffect } from 'react';

import { CircularProgress, Typography, Divider, Button } from '@material-ui/core';

import { useSelector, useDispatch } from 'store';
import { fetchUserSessions } from 'store/settings/sessionsSlice';

import { formatToShortTime } from 'utils/date';

import { useStyles } from './styles';
import { HttpApi } from 'api';

const SessionsSettings: FC = () => {
  const styles = useStyles();
  const dispatch = useDispatch();

  useEffect(() => dispatch(fetchUserSessions()), [dispatch]);

  const { sessions, isLoading } = useSelector(state => state.sessions);

  if (isLoading) {
    return (
      <div className={styles.loading}>
        <CircularProgress />
      </div>
    )
  }

  if (sessions.length === 0) return null;

  const currentSession = sessions.find(session => session.id === localStorage.getItem(HttpApi.AUTH_TOKEN_LOCAL_STORAGE_KEY))!;
  const otherSessions = sessions.filter(session => session.id !== currentSession.id);

  return (
    <>
      <Typography variant="h6" color="textSecondary">
        Current session
      </Typography>

      <div className={styles.session} key={currentSession.id}>
        <div className={styles.sessionDetails}>
          <Typography className={styles.userAgent}>{currentSession.details.userAgent}</Typography>
          <Typography className={styles.remoteAddr} color="textSecondary">{currentSession.details.remoteAddr}</Typography>
        </div>

        <div className={styles.revoke}>
          <Typography variant="body2" color="primary">
            online
          </Typography>

          <Button
            variant="text"
            color="primary"
            className={styles.revokeButton}
          >
            Logout
          </Button>
        </div>
      </div>

      <Typography variant="h6" color="textSecondary" className={styles.otherSessionsTitle}>
        Other sessions
      </Typography>

      <div className={styles.list}>
        {
          otherSessions.map((session) => (
            <>
              <div className={styles.session} key={session.id}>
                <div className={styles.sessionDetails}>
                  <Typography className={styles.userAgent}>{session.details.userAgent}</Typography>
                  <Typography className={styles.remoteAddr} color="textSecondary">{session.details.remoteAddr}</Typography>
                </div>

                <div className={styles.revoke}>
                  <Typography className={styles.lastAccessTime}>
                    {formatToShortTime(session.lastAccessedTime)}
                  </Typography>

                  <Button
                    variant="text"
                    color="primary"
                    className={styles.revokeButton}
                  >
                    Revoke
                  </Button>
                </div>
              </div>

              <Divider variant="fullWidth" />
            </>
          ))
        }
      </div>
    </>
  );
};

export default SessionsSettings;