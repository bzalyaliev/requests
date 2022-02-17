import React from 'react'
import {tableHeader} from "./table-data/data";
import {Styles} from "./styles/requests-page-styles";
import Table from "../components/table";

function RequestsPage() {
    const columns = React.useMemo(
        () => tableHeader,
        []
    )
    // We'll start our table without any data
    const [requests, setRequests] = React.useState([])
    const [loading, setLoading] = React.useState(false)
    const [totalElements, setTotalElements] = React.useState(0)
    const [pageCount, setPageCount] = React.useState(0)
    const [error, setError] = React.useState(0)

    const fetchData = React.useCallback(({pageSize, pageIndex}) => {
        setLoading(true)
        fetch(`/api/requests?page=${pageIndex}&size=${pageSize}`)
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
                setRequests(pageResponse.requests)
                setTotalElements(pageResponse.totalElements)
                setPageCount(pageResponse.totalPages)
                setLoading(false)
            })
            .catch(error => {
                setError(error)
                setLoading(false)
            })
    }, [])

    return (
        <Styles>
            <Table
                columns={columns}
                data={requests}
                fetchData={fetchData}
                loading={loading}
                pageCount={pageCount}
            />
        </Styles>
    )
}

export default RequestsPage
