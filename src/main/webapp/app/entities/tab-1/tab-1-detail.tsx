import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tab-1.reducer';

export const Tab1Detail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tab1Entity = useAppSelector(state => state.tab1.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tab1DetailsHeading">
          <Translate contentKey="dosApp.tab1.detail.title">Tab1</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tab1Entity.id}</dd>
          <dt>
            <span id="col1">
              <Translate contentKey="dosApp.tab1.col1">Col 1</Translate>
            </span>
          </dt>
          <dd>{tab1Entity.col1}</dd>
          <dt>
            <span id="col2">
              <Translate contentKey="dosApp.tab1.col2">Col 2</Translate>
            </span>
          </dt>
          <dd>{tab1Entity.col2}</dd>
          <dt>
            <span id="col3">
              <Translate contentKey="dosApp.tab1.col3">Col 3</Translate>
            </span>
          </dt>
          <dd>{tab1Entity.col3}</dd>
        </dl>
        <Button tag={Link} to="/tab-1" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tab-1/${tab1Entity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default Tab1Detail;
