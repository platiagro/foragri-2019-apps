import React from 'react';
import PropTypes from 'prop-types';
import './style.scss';

import { Button, Modal, Input, Form, Select } from 'antd';

class UrlInput extends React.Component {
  state = {
    url: '',
    device: '',
    isModalVisible: false
  };

  showModal = () => {
    this.setState({ isModalVisible: true });
  };

  setUrl = (e) => {
    this.setState({ url: e.target.value })
  }

  setDevice = (e) => {
    this.setState({ device: e.target.value })
  }

  handleOk = () => {
    this.setState({ isModalVisible: false });
    this.props.onUrlSet(this.state.url, this.state.device);
  }

  handleCancel = () => {
    this.setState({ isModalVisible: false });
  }

  render() {
    const { isModalVisible, url, device } = this.state;
    const { theme } = this.props;
    return (
      <>
        <Modal
          title='Informar URL'
          width={600}
          visible={isModalVisible}
          onOk={this.handleOk}
          onCancel={this.handleCancel}
          okText='OK'
          cancelText='Cancelar'
          okButtonProps={{className: theme}}
        >
          <Form.Item label='URL'>
            <Input placeholder='URL' value={url} onChange={this.setUrl} onPressEnter={this.handleOk} />
          </Form.Item>

          <Form.Item label='Controlador de Irrigação'>
            <Select
              style={{ width: 300 }}
              placeholder='Selecione'
              value={device}
              onChange={this.setDevice}>

              <Select.Option key='72269c'>Controlador de Irrigação 1</Select.Option>
              <Select.Option key='394c6a'>Controlador de Irrigação 2</Select.Option>
              <Select.Option key='279c65'>Controlador de Irrigação 3</Select.Option>
            </Select>
          </Form.Item>
        </Modal>

        <div className='center-box'>
          <h1>URL do fluxo</h1>
          <p>Informe a URL do fluxo gerada na implantação</p>
          <Button
            className={theme}
            onClick={this.showModal}>
            Informar URL
          </Button>
        </div>
      </>
    );
  }
}


UrlInput.propTypes = {
  onUrlSet: PropTypes.func,
  theme: PropTypes.string
};

export default UrlInput;
