import React from "react";
import classes from "./PaginationBlock.module.css";

const PaginationBlock = React.memo(({ currentPage, setCurrentPage, total }) => {
  const lastPage = Math.ceil(total / 5);

  const onPrev = () => {
    if (currentPage <= 1) {
      return;
    }
    setCurrentPage(currentPage - 1);
  };

  const onNext = () => {
    if (currentPage >= lastPage) {
      return;
    }
    setCurrentPage(currentPage + 1);
  };

  return (
    <div className={classes.pagination}>
      <span>{`${currentPage} of ${lastPage}`}</span>
      <div>
        <div
          className={`${classes.arrow} ${classes.left} ${
            currentPage === 1 ? classes.disabled : ""
          }`}
        >
          <i onClick={onPrev}></i>
        </div>
        <div
          className={`${classes.arrow} ${classes.right} ${
            currentPage === lastPage ? classes.disabled : ""
          }`}
        >
          <i onClick={onNext}></i>
        </div>
      </div>
    </div>
  );
});

export default PaginationBlock;
