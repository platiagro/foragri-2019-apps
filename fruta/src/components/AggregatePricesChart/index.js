import React from 'react';

import { Bar } from 'react-chartjs-2';

// import mock_data from './chart_mock';

const AggregatePricesChart = (props) => {
  const { data } = props;
  return (
    <>
      <Bar
        data={data}
        options={{
          legend: {
            labels: {
              fontFamily: 'Open Sans',
              fontSize: 14
            }
          },
          cornerRadius: 2,
          scales: {
            xAxes: [{
              maxBarThickness: 42,
              gridLines: {
                display: false
              },
              ticks: {
                fontColor: '#333',
                fontFamily: 'Open Sans',
                fontSize: 14
              }
            }],
            yAxes: [{
              gridLines: {
                display: false
              },
              min: 0,
              ticks: {
                fontColor: '#333',
                fontFamily: 'Open Sans',
                fontSize: 14,
                beginAtZero: true,
              }
            }]
          }
        }}
      />
    </>
  );
};

export default AggregatePricesChart;
