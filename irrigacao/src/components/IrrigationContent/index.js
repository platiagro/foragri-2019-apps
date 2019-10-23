import React from 'react';
import PropTypes from 'prop-types';
import './style.scss';

import { message } from 'antd';

import UrlInput from '../UrlInput';

import { api } from '../../services/api';

class IrrigationContent extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      url: '',
      device: ''
    };
  }

  handleUrlSet = async (url, device) => {
    this.setState({ url: url, device: device });

    const response = await api.post('/auth', {
      'username': 'admin',
      'passwd': 'admin'
    });

    if (response.status === 200) {
      const response2 = await api.post(`/device/${device}/actuate`, {
        "attrs": {
          "url": url
        }
      });

      if (response2.status !== 200) {
        message.error('Não foi possível atuar no dispositivo.');
      }
    } else {
      message.error('Não foi possível acessar a Plataforma DOJOT.');
    }
  }

  render() {
    return (
      <>
        <UrlInput theme='blue' onUrlSet={this.handleUrlSet} />
      </>
    );
  }
}

IrrigationContent.propTypes = {
  data: PropTypes.array
};

export default IrrigationContent;
