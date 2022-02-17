import React from 'react'
import styled from 'styled-components'
import {useTable, usePagination} from 'react-table'

const json = require('../stub/requests-page-1-response.json')

const Styles = styled.div`
  padding: 1rem;

  table {
    border-spacing: 0;
    border: 1px solid black;

    tr {
      :last-child {
        td {
          border-bottom: 0;
        }
      }
    }

    th,
    td {
      margin: 0;
      padding: 0.5rem;
      border-bottom: 1px solid black;
      border-right: 1px solid black;

      :last-child {
        border-right: 0;
      }
    }
  }

  .pagination {
    padding: 0.5rem;
  }
`

// Let's add a fetchData method to our Table component that will be used to fetch
// new data when pagination state changes
// We can also add a loading state to let our table know it's loading new data
function Table({
                   columns,
                   data,
                   fetchData,
                   loading,
                   pageCount: controlledPageCount,
               }) {
    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        prepareRow,
        page,
        canPreviousPage,
        canNextPage,
        pageOptions,
        pageCount,
        gotoPage,
        nextPage,
        previousPage,
        setPageSize,
        // Get the state from the instance
        state: {pageIndex, pageSize},
    } = useTable(
        {
            columns,
            data,
            initialState: {pageIndex: 0}, // Pass our hoisted table state
            manualPagination: true, // Tell the usePagination
            // hook that we'll handle our own data fetching
            // This means we'll also have to provide our own
            // pageCount.
            pageCount: controlledPageCount,
        },
        usePagination
    )

    // Listen for changes in pagination and use the state to fetch our new data
    React.useEffect(() => {
        fetchData({pageIndex, pageSize})
    }, [fetchData, pageIndex, pageSize])

    // Render the UI for your table
    return (
        <>
      <pre>
        <code>
          {JSON.stringify(
              {
                  pageIndex,
                  pageSize,
                  pageCount,
                  canNextPage,
                  canPreviousPage,
              },
              null,
              2
          )}
        </code>
      </pre>
            <table {...getTableProps()}>
                <thead>
                {headerGroups.map(headerGroup => (
                    <tr {...headerGroup.getHeaderGroupProps()}>
                        {headerGroup.headers.map(column => (
                            <th {...column.getHeaderProps()}>
                                {column.render('Header')}
                                <span>
                    {column.isSorted
                        ? column.isSortedDesc
                            ? ' 🔽'
                            : ' 🔼'
                        : ''}
                  </span>
                            </th>
                        ))}
                    </tr>
                ))}
                </thead>
                <tbody {...getTableBodyProps()}>
                {page.map((row, i) => {
                    prepareRow(row)
                    return (
                        <tr {...row.getRowProps()}>
                            {row.cells.map(cell => {
                                return <td {...cell.getCellProps()}>{cell.render('Cell')}</td>
                            })}
                        </tr>
                    )
                })}
                <tr>
                    {loading ? (
                        // Use our custom loading state to show a loading indicator
                        <td colSpan="10000">Loading...</td>
                    ) : (
                        <td colSpan="10000">
                            Showing {page.length} of ~{controlledPageCount * pageSize}{' '}
                            results
                        </td>
                    )}
                </tr>
                </tbody>
            </table>
            {/*
        Pagination can be built however you'd like.
        This is just a very basic UI implementation:
      */}
            <div className="pagination">
                <button onClick={() => gotoPage(0)} disabled={!canPreviousPage}>
                    {'<<'}
                </button>
                {' '}
                <button onClick={() => previousPage()} disabled={!canPreviousPage}>
                    {'<'}
                </button>
                {' '}
                <button onClick={() => nextPage()} disabled={!canNextPage}>
                    {'>'}
                </button>
                {' '}
                <button onClick={() => gotoPage(pageCount - 1)} disabled={!canNextPage}>
                    {'>>'}
                </button>
                {' '}
                <span>
          Page{' '}
                    <strong>
            {pageIndex + 1} of {pageOptions.length}
          </strong>{' '}
        </span>
                <span>
          | Go to page:{' '}
                    <input
                        type="number"
                        defaultValue={pageIndex + 1}
                        onChange={e => {
                            const page = e.target.value ? Number(e.target.value) - 1 : 0
                            gotoPage(page)
                        }}
                        style={{width: '100px'}}
                    />
        </span>{' '}
                <select
                    value={pageSize}
                    onChange={e => {
                        setPageSize(Number(e.target.value))
                    }}
                >
                    {[10, 20, 30, 40, 50].map(pageSize => (
                        <option key={pageSize} value={pageSize}>
                            Show {pageSize}
                        </option>
                    ))}
                </select>
            </div>
        </>
    )
}

function App() {
    const columns = React.useMemo(
        () => [
            {
                Header: 'ID заявки',
                accessor: 'id'
            },
            {
                Header: 'Дата заявки',
                accessor: 'date'
            },
            {
                Header: 'Статус',
                accessor: 'status'
            },
            {
                Header: 'Инициатор',
                accessor: 'originator'
            },
            {
                Header: 'Тип',
                accessor: 'type'
            },
            {
                Header: 'Масса',
                accessor: 'mass'
            },
            {
                Header: 'Срок завершения',
                accessor: 'deadline'
            },
            {
                Header: 'Задача',
                accessor: 'objective'
            },
            {
                Header: 'Комментарии',
                accessor: 'comments',
            }
        ],
        []
    )
    // We'll start our table without any data
    const [requests, setRequests] = React.useState([])
    const [loading, setLoading] = React.useState(false)
    const [elementsPerPage] = React.useState(10)
    const [totalElements, setTotalElements] = React.useState(0)
    const [pageCount, setPageCount] = React.useState(0)

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
                setRequests(json.requests)
                setTotalElements(json.totalElements)
                setPageCount(json.totalPages)
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

export default App
