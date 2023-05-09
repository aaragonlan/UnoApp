import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITab1 } from 'app/shared/model/tab-1.model';
import { getEntity, updateEntity, createEntity, reset } from './tab-1.reducer';

export const Tab1Update = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tab1Entity = useAppSelector(state => state.tab1.entity);
  const loading = useAppSelector(state => state.tab1.loading);
  const updating = useAppSelector(state => state.tab1.updating);
  const updateSuccess = useAppSelector(state => state.tab1.updateSuccess);

  const handleClose = () => {
    navigate('/tab-1');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...tab1Entity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...tab1Entity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dosApp.tab1.home.createOrEditLabel" data-cy="Tab1CreateUpdateHeading">
            <Translate contentKey="dosApp.tab1.home.createOrEditLabel">Create or edit a Tab1</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="tab-1-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('dosApp.tab1.col1')} id="tab-1-col1" name="col1" data-cy="col1" type="text" />
              <ValidatedField label={translate('dosApp.tab1.col2')} id="tab-1-col2" name="col2" data-cy="col2" type="text" />
              <ValidatedField label={translate('dosApp.tab1.col3')} id="tab-1-col3" name="col3" data-cy="col3" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tab-1" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default Tab1Update;
