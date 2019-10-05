import React from 'react';
import PropTypes from 'prop-types';
import './style.scss';

import { Row, Col, Button, Modal, Input, Select, Form } from 'antd';

import UrlInput from '../UrlInput';
import MeasuresChart from '../MeasuresChart';
import FailureChart from '../FailureChart';
import machines from './machines_mock';

import { post } from '../../services/api';

const { Option } = Select;

class MachineContent extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      url: '',
      isModalVisible: false,
      machine: 'Afrodite',
      machineIndex: 0,
      score: '',
      measuresData: {},
      failureData: {}
    };
    this.dataList = [];
    this.predictionList = [];
  }

  async componentWillMount() {
    this.setUrl(this.state.url);
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

  setUrl = (url) => {
    this.setState({ url: url });
    let row = 0;
    this.postData(this.state.url, row, row += 24);
    this.interval = setInterval(() => this.postData(this.state.url, row, row += 24), 4000);
  }

  handleMachineSelected = (machine) => {
    this.setState({ machine: machine, machineIndex: machines.indexOf(machine) });
  }

  async postData(url, start, end) {
    const { data } = this.props;
    const ndarray = data.slice(start % data.length, end % data.length).map(Object.values);
    const names = Object.keys(data[0]);
    const response = await post(
      url, {
      'data': {
        'ndarray': ndarray,
        'names': names
      }
    });
    if (response.status === 200) {
      this.dataList = [...this.dataList, ndarray];
      this.predictionList = [...this.predictionList, response.data.data.ndarray];

      const latestData = this.dataList.slice(-5);
      const latestPrediction = this.predictionList.slice(-5);

      const failureData = { datasets: [] };

      const colors = ['#E0A216', '#2EB8A6', '#0094AD', '#FA7E19'];
      const yesIndex = response.data.data.names.findIndex(n => n === '1');
      machines.forEach((machine, index) => {
        failureData.datasets.push({
          label: machine,
          backgroundColor: colors[index % colors.length],
          data: latestPrediction.map(prediction => {
            const y = prediction[index][yesIndex];
            const r = y * 50 + 5;
            return { x: index + 1, y: y, r: r }
          })
        })
      });

      let score = this.state.score;
      let measuresData = this.state.measuresData;
      if (this.state.machineIndex > -1) {
        measuresData = { labels: [], datasets: [] };
        measuresData.labels = ["Vibração 1","Vibração 2","Vibração 3","Vibração 4","Vibração 5","Vibração 6"];
        measuresData.datasets.push({
          label: 'Medidas de Vibração',
          backgroundColor: '#0094AD',
          borderColor: '#0094AD',
          borderWidth: 1,
          data: latestData[latestData.length - 1][this.state.machineIndex].slice(4, 10)
        });
        score = latestPrediction[latestPrediction.length - 1][this.state.machineIndex][yesIndex].toFixed(2).replace('.', ',');
      }

      this.setState({
        score: score,
        measuresData: measuresData,
        failureData: failureData
      });
    }
  }

  render() {
    const { url, isModalVisible, machine, measuresData, failureData } = this.state;
    return (
      <>
        {url === '' ? (
          <UrlInput theme='orange' onUrlSet={this.setUrl} />
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
                okButtonProps={{ className: 'orange' }}
              >
                <Input placeholder='URL' onChange={e => this.setUrl(e.target.value)} value={url} onPressEnter={this.handleOk} />
              </Modal>

              <Form layout='inline'>
                <Form.Item label='Máquinas'>
                  <Select
                    style={{ width: 300 }}
                    placeholder='Selecione'
                    value={machine}
                    onChange={this.handleMachineSelected}>
                    {machines.map((machine) => (
                      <Option key={machine}>{machine}</Option>
                    ))}
                  </Select>
                </Form.Item>
                <Form.Item style={{ float: 'right', margin: 0 }}>
                  <Button className='orange' style={{ width: 100 }} onClick={this.showModal}>URL</Button>
                </Form.Item>
              </Form>
              <Row gutter={16}>
                <Col span={6}>
                  <div className='white-box no-padding'>
                    <div className='white-box-header'>Falha | <span className='blue'>{this.state.machine}</span></div>

                    <div className='white-box-content'>
                      <Button shape='circle' className='score'>
                        {this.state.score}
                      </Button>

                      <div className='text-center'>probabilidade de falha</div>
                    </div>
                  </div>
                </Col>
                <Col span={18}>
                  <div className='white-box no-padding'>
                    <div className='white-box-header'>Medidas de vibração | <span className='blue'>{this.state.machine}</span></div>
                    <div className='white-box-content'>
                      <MeasuresChart data={measuresData} />
                    </div>
                  </div>
                </Col>
              </Row>
              <Row>
                <Col span={24}>
                  <div className='white-box no-padding' style={{height: 400}}>
                    <div className='white-box-header'>Probabilidade de falha  | <span className='blue'>Todas as máquinas</span></div>
                    <div className='white-box-content'>
                      <FailureChart data={failureData} />
                    </div>
                  </div>
                </Col>
              </Row>
            </>
          )}
      </>
    );
  }
}

MachineContent.propTypes = {
  data: PropTypes.array
};

export default MachineContent;
