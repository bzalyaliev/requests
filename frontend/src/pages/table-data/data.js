const moment = require('moment');
//see https://react-data-table-component.netlify.app/?path=/docs/api-columns--page
/*export const columns = React.useMemo(
    () => [
        {
            Header: 'ID заявки',
            selector: 'id'
        },
        {
            Header: 'Дата заявки',
            selector: 'date'
        },
        {
            Header: 'Статус',
            selector: row => row.status,
            width: '120px'
        },
    ], [])*/

export const columns = [
    {
        name: 'ID заявки',
        selector: row => row.id,
        width: '90px'
    },
    {
        name: 'Дата заявки',
        selector: row => row.date,
        width: '180px',
        format: row => moment(row.date).format('DD-MM-YYYY HH:MM:SS')
    },
    {
        name: 'Статус',
        selector: row => row.status,
        width: '120px'
    },
    {
        name: 'Инициатор',
        selector: row => row.originator,
        width: '100px'
    },
    {
        name: 'Тип',
        selector: row => row.type,
        width: '100px'
    },
    {
        name: 'Масса',
        selector: row => row.mass,
        width: '100px'
    },
    {
        name: 'Срок завершения',
        selector: row => row.deadline,
        format: row => moment(row.deadline).format('DD-MM-YYYY HH:MM:SS'),
        width: '200px'
    },
    {
        name: 'Задача',
        selector: row => row.objective,
    },
    {
        name: 'Комментарии',
        selector: row => row.comments,
    },
]
