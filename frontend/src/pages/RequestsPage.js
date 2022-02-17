import React, {Component, useState} from "react";
import DataTable from 'react-data-table-component';
import {columns} from "./table-data/data";
import RequestsPagination from "./CustomPagination";

const json = require('../stub/requests-page-1-response.json')

class RequestsPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            requests: [],
            currentPage: 0,
            elementsPerPage: 10,
            totalElements: undefined
        };

        this.fetchData = this.fetchData.bind(this)
    }

    fetchData() {
        const {currentPage, elementsPerPage} = this.state
        fetch(`/api/requests?page=${currentPage}&size=${elementsPerPage}`)
            .then(response => {
                if (response.status !== 200) {
                    throw {
                        error: {
                            status: response.status
                        }
                    }
                } else {
                    return response.json()
                }
            })
            .then(pageResponse => {
                this.setState(prevState => ({
                    ...prevState,
                    requests: json.requests,
                    totalElements: json.totalElements
                }))
            })
            .catch(error => {
                /*console.log(error)
                this.setState(prevState => ({
                    ...prevState,
                    response: error
                }))*/
                this.setState(prevState => ({
                    ...prevState,
                    requests: json.requests,
                    totalElements: json.totalElements
                }))
            })
    }

    componentDidMount() {
        this.fetchData()
    }

    render() {
        return (
            <div>
                {myPagination.call(this)}
            </div>
         /*   <MyPagination currentPage={this.state.currentPage}
                          elementsPerPage={this.state.elementsPerPage}
                          requests={this.state.requests}
                          totalElements={this.state.totalElements}
            />*/
        )
    }
}

function myPagination() {
    const {currentPage, elementsPerPage, requests, totalElements} = this.state
    return (
        <DataTable
            title={"Все запросы"}
            columns={columns}
            data={requests}
            pagination
            //     onChangePage={(event, page) => this.fetchData()}
            paginationDefaultPage={currentPage}
            paginationTotalRows={totalElements}
            paginationPerPage={elementsPerPage}
            paginationRowsPerPageOptions={[10, 20, 50]}
        />
    )
}

export default RequestsPage;