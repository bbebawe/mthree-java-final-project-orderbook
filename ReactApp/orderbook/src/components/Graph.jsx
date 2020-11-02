import React from "react";
import Chart from "react-apexcharts";
import "./style/page.css";

export default class ApexChart extends React.Component {
  render() {
    console.log("great success");
    let series = [
      {
        name: "Price",
        data: this.props.dataPrice,
      },
    ];
    let options = {
      chart: {
        type: "line",
        zoom: {
          enabled: true,
        },
      },
      dataLabels: {
        enabled: false,
      },
      stroke: {
        curve: "straight",
      },
      colors: ["#474e78"],
      grid: {
        row: {
          colors: ["#96a1e3", "transparent"],
        },
      },
      xaxis: {
        categories: this.props.dataTime,
        labels: {
            show: true,
        },
        tickAmount: 4,
        floating: true,
        title: {
          text: "Time",
        },
      },
      yaxis: {
        tickAmount: 12,
        title: {
          text: "Price",
        },
      },
      
        responsive: [{
            breakpoint: 100,
        }]

    };
    return (
      <div className="chart">
        <Chart
          options={options}
          series={series}
          type="line"

          width="100%"
        />
      </div>
    );
  }
}
