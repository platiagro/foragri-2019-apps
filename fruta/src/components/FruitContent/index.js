import React from 'react';
import PropTypes from 'prop-types';
import './style.scss';

import { Row, Col, Button, Modal, Input, Select, Form } from 'antd';

import UrlInput from '../UrlInput';
import LatestPricesChart from '../LatestPricesChart';
import AggregatePricesChart from '../AggregatePricesChart';
import fruits from './fruits_mock';

import { post } from '../../services/api';

const { Option } = Select;

const FruitBox = (props) => (
  <div className={`white-box ${props.name === 'Nome da fruta' ? 'disabled' : '' }`}>
    <div className='white-box-title'>Preço estimado</div>
    <div className='white-box-name'>{props.name}</div>
    <div className='white-box-price'>{props.price}</div>
    <div className='white-box-chart'>
      {props.data && props.data.labels && <LatestPricesChart data={props.data} />}
    </div>
    <div className='white-box-divider'></div>
    <Button disabled>Negociar</Button>
  </div>
);

class FruitContent extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      url: '',
      isModalVisible: false,
      fruitList: [],
      fruitIndexList: [],
      priceList: [], //prediction history for selected fruits
      predictionData: [], //prediction data as expected
      aggregateData: {}
    };
    this.dataList = new Array(fruits.length).fill(null).map(n => []);
    this.predictionList = new Array(fruits.length).fill(null).map(n => []);
  }

  async componentWillUnmount() {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

  showModal = () => {
    this.setState({ isModalVisible: true });
  };

  handleOk = () => {
    this.setState({ isModalVisible: false });
    this.props.onUrlSet(this.state.url);
  }

  handleCancel = () => {
    this.setState({ isModalVisible: false });
  }

  handleUrlSet = (url) => {
    const { data } = this.props;

    this.setState({ url: url });

    if (this.interval) {
      clearInterval(this.interval);
    }

    this.dataList = new Array(fruits.length).fill(null).map(n => []);
    this.predictionList = new Array(fruits.length).fill(null).map(n => []);

    let i = 0;
    let date = data[i]["Data"];
    this.postData(url, date);
    // Finds next date
    while (++i < data.length) {
      if (data[i]["Data"] !== date) {
        date = data[i]["Data"];
        break;
      }
    }

    // Every 4 seconds, sends data from a date for prediction
    this.interval = setInterval(() => {
      this.postData(url, date);
      // Finds next date
      while (++i < data.length) {
        if (data[i]["Data"] !== date) {
          date = data[i]["Data"];
          break;
        }
      }
      // After all data has been sent,
      // resets counter and starts all over again
      if (i === data.length) {
        i = 0;
        date = data[i]["Data"];
      }
    }, 4000);
  }

  handleFruitSelected = (fruitList) => {
    const fruitIndexList = fruitList.map(fruit => {
      return fruits.indexOf(fruit);
    });
    this.setState({
      fruitList: fruitList,
      fruitIndexList: fruitIndexList
    });
  }

  async postData(url, date) {
    const { data } = this.props;
    const ndarray = data.filter(d => d["Data"] === date).map(Object.values);
    const names = Object.keys(data[0]);
    const response = await post(
      url, {
      'data': {
        'ndarray': ndarray,
        'names': names
      }
    });
    if (response.status === 200) {
      ndarray.forEach((arr, idx) => {
      const fruitIndex = fruits.findIndex(fruit => fruit === arr[1]);
        this.dataList[fruitIndex].push(arr);
        this.predictionList[fruitIndex].push(response.data.data.ndarray[idx]);
      });

      const priceList = this.state.fruitIndexList.map(index => {
        return this.predictionList[index][this.predictionList[index].length - 1].toFixed(2).replace('.', ',');
      });

      const predictionData = this.state.fruitIndexList.map(index => {
        return {
          labels: this.predictionList[index].slice(-15).map(data => data[0].find(d => /\d+-\d+-\d/.test(d))),
          datasets: [{
            data: this.predictionList[index].slice(-15).map(p => p[index]),
            fill: true,
            backgroundColor: '#975fe4'
          }]
        };
      });

      const aggregateData = { labels: [], datasets: [] };
      aggregateData.labels = this.state.fruitList;
      aggregateData.datasets = aggregateData.datasets.concat([{
        label: 'Venda acumulada',
        backgroundColor: '#975FE4',
        data: this.state.fruitList.map((fruit, index) => {
          const fruitIndex = this.state.fruitIndexList[index];
          // Predicted_price * Producao_estimada
          return this.predictionList[fruitIndex].slice(-15).reduce((sum, p, idx) => sum + p * this.dataList[fruitIndex][idx][3], 0);
        })
      }, {
        label: 'Custo acumulado',
        backgroundColor: '#550055',
        data: this.state.fruitList.map((fruit, index) => {
          const fruitIndex = this.state.fruitIndexList[index];
          // defensivos_unidade_hc * 10
          return this.dataList[fruitIndex].slice(-15).reduce((sum, p) => sum + p[11] * 10, 0);
        })
      }]);

      this.setState({
        priceList: priceList,
        predictionData: predictionData,
        aggregateData: aggregateData
      });
    }
  }

  render() {
    const { url, isModalVisible, fruitList, priceList, predictionData, aggregateData } = this.state;
    return (
      <>
        {url === '' ? (
          <UrlInput theme='green' onUrlSet={this.handleUrlSet} />
        ) : (
            <>
              <Modal
                title='Informar URL'
                width={600}
                visible={isModalVisible}
                onOk={this.handleOk}
                onCancel={this.handleCancel}
                okText='OK'
                cancelText='Cancelar'
                okButtonProps={{className: 'green'}}
              >
                <Input placeholder='URL' onChange={e => this.setUrl(e.target.value)} value={url} onPressEnter={this.handleOk}/>
              </Modal>

              <Form layout='inline'>
                <Form.Item label='Frutas'>
                  <Select
                    mode='multiple'
                    style={{ width: 800 }}
                    placeholder='Selecione'
                    onChange={this.handleFruitSelected}>
                    {fruits.map((fruit) => (
                      <Option key={fruit}>{fruit}</Option>
                    ))}
                  </Select>
                </Form.Item>
                <Form.Item style={{float: 'right', margin: 0}}>
                  <Button className='green' style={{width: 100}} onClick={this.showModal}>URL</Button>
                </Form.Item>
              </Form>
              <Row gutter={16}>
                <Col span={6}>
                  <FruitBox
                    name={fruitList.length > 0 ? fruitList[0] : 'Nome da fruta'}
                    price={priceList.length > 0 ? priceList[0] : '0,00' }
                    data={predictionData.length > 0 ? predictionData[0] : []}
                  />
                </Col>
                <Col span={6}>
                  <FruitBox
                    name={fruitList.length > 1 ? fruitList[1] : 'Nome da fruta'}
                    price={priceList.length > 1 ? priceList[1] : '0,00' }
                    data={predictionData.length > 1 ? predictionData[1] : []}
                  />
                </Col>
                <Col span={6}>
                  <FruitBox
                    name={fruitList.length > 2 ? fruitList[2] : 'Nome da fruta'}
                    price={priceList.length > 2 ? priceList[2] : '0,00' }
                    data={predictionData.length > 2 ? predictionData[2] : []}
                  />
                </Col>
                <Col span={6}>
                  <FruitBox
                    name={fruitList.length > 3 ? fruitList[3] : 'Nome da fruta'}
                    price={priceList.length > 3 ? priceList[3] : '0,00' }
                    data={predictionData.length > 3 ? predictionData[3] : []}
                  />
                </Col>
              </Row>
              <Row>
                <Col span={24}>
                  <div className='white-box' style={{height: 450}}>
                    <Col span={16}>
                      <span style={{fontWeight: 'bold'}}>Valor Agregado</span>
                      {fruitList.length > 0 && <AggregatePricesChart data={aggregateData} />}
                    </Col>
                  </div>
                </Col>
              </Row>
            </>
          )}
      </>
    );
  }
}

FruitContent.propTypes = {
  data: PropTypes.array
};

export default FruitContent;
