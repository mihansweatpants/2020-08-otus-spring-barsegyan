import { useEffect } from 'react';

import { StompClient } from 'api';
import { StompMessage, StompMessageType } from 'api/types/base/stomp';

import { useDispatch } from 'store';
import { pushRecievedMessage } from 'store/messenger/messagesSlice';

export const useStompListener = () => {
  const dispatch = useDispatch();

  useEffect(
    () => {
      const onMessageRecieved = (message: StompMessage) => {
        switch (message.type) {
          case StompMessageType.NEW_CHAT_MESSAGE:
            dispatch(pushRecievedMessage(message.payload));
            break;

          default:
            break;
        }
      };

      StompClient.connect(onMessageRecieved);
    },
    [dispatch],
  );
};
