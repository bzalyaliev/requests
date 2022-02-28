const moment = require('moment');

export const tableHeader = [
    {
        Header: 'ID заявки',
        accessor: 'id',
        width: '90px'
    },
    {
        Header: 'Дата заявки',
        accessor: 'date',
        Cell: ({ value }) => moment(value).format('DD-MM-YYYY HH:MM:SS'),
        width: '180px'
    },
    {
        Header: 'Статус',
        accessor: 'status',
        width: '120px'
    },
    {
        Header: 'Инициатор',
        accessor: 'originator',
        width: '100px'
    },
    {
        Header: 'Тип',
        accessor: 'type',
        width: '100px'
    },
    {
        Header: 'Масса',
        accessor: 'mass',
        width: '100px'
    },
    {
        Header: 'Срок завершения',
        accessor: 'deadline',
        Cell: ({ value }) => moment(value).format('DD-MM-YYYY HH:MM:SS'),
        width: '200px'
    },
    {
        Header: 'Задача',
        accessor: 'objective'
    },
    {
        Header: 'Комментарии',
        accessor: 'comments',
    }
]
