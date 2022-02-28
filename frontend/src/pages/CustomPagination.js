import {Component} from "react";
import {TablePagination} from "@material-ui/core";
import TablePaginationActions from "@material-ui/core/TablePagination/TablePaginationActions";

const RequestsPagination = ({ rowsPerPage, rowCount, onChangePage, onChangeRowsPerPage, currentPage }) => (
    <TablePagination
        component="nav"
        count={rowCount}
        rowsPerPage={rowsPerPage}
        page={currentPage - 1}
        onPageChange={onChangePage}
        onChangeRowsPerPage={({ target }) => onChangeRowsPerPage(Number(target.value))}
        ActionsComponent={TablePaginationActions}
    />
)

export default RequestsPagination;