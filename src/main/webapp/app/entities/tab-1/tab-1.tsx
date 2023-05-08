import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITab1 } from 'app/shared/model/tab-1.model';
import { getEntities } from './tab-1.reducer';

export const Tab1 = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const tab1List = useAppSelector(state => state.tab1.entities);
  const loading = useAppSelector(state => state.tab1.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="tab-1-heading" data-cy="Tab1Heading">
        <Translate contentKey="unoApp.tab1.home.title">Tab 1 S</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="unoApp.tab1.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/tab-1/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="unoApp.tab1.home.createLabel">Create new Tab 1</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tab1List && tab1List.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="unoApp.tab1.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="unoApp.tab1.col1">Col 1</Translate>
                </th>
                <th>
                  <Translate contentKey="unoApp.tab1.col2">Col 2</Translate>
                </th>
                <th>
                  <Translate contentKey="unoApp.tab1.col3">Col 3</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tab1List.map((tab1, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/tab-1/${tab1.id}`} color="link" size="sm">
                      {tab1.id}
                    </Button>
                  </td>
                  <td>{tab1.col1}</td>
                  <td>{tab1.col2}</td>
                  <td>{tab1.col3}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/tab-1/${tab1.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/tab-1/${tab1.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/tab-1/${tab1.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="unoApp.tab1.home.notFound">No Tab 1 S found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Tab1;
