import { useState } from 'react';

export const usePagination = () => {
  const [page, setPage] = useState(0);
  const [limit, setLimit] = useState(10);
  const [totalItems, setTotalItems] = useState(0);

  return {
    page,
    setPage,

    limit,
    setLimit,

    totalItems,
    setTotalItems,
  }
};
