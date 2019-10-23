import React from 'react';

import { Line } from 'react-chartjs-2';
// import mock_data from './chart_mock';

const LatestPricesChart = (props) => {
  const { data } = props;
  console.log(data);
  return (
    <>
      <Line
        data={data}
        height={50}
        options={{
          maintainAspectRatio: false,
          elements: {
            point: { radius: 0 }
          },
          showLine: false,
          legend: {
            display: false
          },
          scales: {
            xAxes: [{
              display: false,
              gridLines: {
                display: false,
                drawBorder: false
              },
              type: 'time',
              time: {
                parser: 'YYYY-MM-DD',
                unit: 'day',
                unitStepSize: 1,
                displayFormats: {
                  'day': 'DD/MM/YYYY'
                }
              }
            }],
            yAxes: [{
              ticks: {
                beginAtZero: true,
              },
              display: false,
              gridLines: {
                display: false,
                drawBorder: false
              }
            }]
          },
          tooltips: {
            callbacks: {
              label: function (tooltipItem) {
                return tooltipItem.yLabel;
              }
            }
          }
        }}
      />
    </>
  );
};

export default LatestPricesChart;
